'use client';

import React, { useState, useEffect } from 'react';
import { ActiveIdProvider } from './_contexts/ActiveIdContext';
import { MenuItemsProvider } from './_contexts/MenuItemsContext';
import { ActiveIdProvider, useActiveId } from './_contexts/ActiveIdContext';
import { MenuItemsProvider, useMenuItems } from './_contexts/MenuItemsContext';
import { EditorProvider } from './_contexts/EditorContext';
import { Editor } from './_components/Editor';
import { LeftBar } from './_components/LeftBar';
import { LiveblocksProvider, RoomProvider } from '@liveblocks/react/suspense';
import { v4 as uuidv4 } from 'uuid';
import Nav from './_components/Nav';
import { MarkdownProvider } from './_contexts/MarkdownContext';
import RightMain from './_components/RightMain';
import LeftMain from './_components/LeftMain';

const INITIAL_MENU_ITEMS = [
  { id: uuidv4(), label: 'Dashboard' },
  { id: uuidv4(), label: 'Projects' },
  { id: uuidv4(), label: 'Tasks' },
  { id: uuidv4(), label: 'Messages' },
  { id: uuidv4(), label: 'Settings' },
  { id: uuidv4(), label: 'Dashboard' },
  { id: uuidv4(), label: 'Projects' },
  { id: uuidv4(), label: 'Tasks' },
  { id: uuidv4(), label: 'Messages' },
  { id: uuidv4(), label: 'Settings' },
];

const LEFT_SIDEBAR_WIDTH = 320;
const COLLAPSED_LEFT_SIDEBAR_WIDTH = 80;

export default function EditPage() {
  const [leftSize, setLeftSize] = useState(50);
  const [isDragging, setIsDragging] = useState(false);
  const [isVertical, setIsVertical] = useState(window.innerWidth < 768);
  const [isCollapsed, setIsCollapsed] = useState(window.innerWidth < 1400);

  const handleMouseDown = () => setIsDragging(true);

  const handleMouseMove = (e: MouseEvent) => {
    if (isDragging) {
      if (isVertical) {
        // 세로 방향일 때 (상단/하단 높이 조정)
        const adjustedY = e.clientY - 72; // 72px은 Nav 높이로 가정
        const newLeftHeight = (adjustedY / (window.innerHeight - 72)) * 100;
        if (newLeftHeight > 10 && newLeftHeight < 90) {
          setLeftSize(newLeftHeight);
        }
      } else {
        // 가로 방향일 때 (좌/우 너비 조정)
        const adjustedX = e.clientX - (isCollapsed ? COLLAPSED_LEFT_SIDEBAR_WIDTH : LEFT_SIDEBAR_WIDTH);
        const newLeftWidth =
          (adjustedX / (window.innerWidth - (isCollapsed ? COLLAPSED_LEFT_SIDEBAR_WIDTH : LEFT_SIDEBAR_WIDTH))) * 100;
        if (newLeftWidth > 10 && newLeftWidth < 90) {
          setLeftSize(newLeftWidth);
        }
      }
    }
  };

  const handleMouseUp = () => {
    setIsDragging(false);
  };

  useEffect(() => {
    if (isDragging) {
      window.addEventListener('mousemove', handleMouseMove);
      window.addEventListener('mouseup', handleMouseUp);
    } else {
      window.removeEventListener('mousemove', handleMouseMove);
      window.removeEventListener('mouseup', handleMouseUp);
    }

    // 컴포넌트가 언마운트될 때 이벤트 리스너 제거
    return () => {
      window.removeEventListener('mousemove', handleMouseMove);
      window.removeEventListener('mouseup', handleMouseUp);
    };
  }, [isDragging]);

  useEffect(() => {
    const handleResize = () => {
      setIsVertical(window.innerWidth < 768); // md 기준 (768px)
      setIsCollapsed(window.innerWidth < 1400); // 1400px 미만일 때 LeftBar 접힘
    };
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);

  return (
    <ActiveIdProvider>
      <MenuItemsProvider>
        <MarkdownProvider>
          <LiveblocksProvider authEndpoint="/api/liveblocks-auth">
            <div className="flex flex-col bg-gray-700 h-full">
              <RoomProvider
                id="sidebar-room-2"
                initialStorage={{
                  initialMenuItems: INITIAL_MENU_ITEMS,
                  menuItems: INITIAL_MENU_ITEMS,
                }}
              >
                <Nav />
              </RoomProvider>
          <EditorProvider>
            <LiveblocksProvider authEndpoint="/api/liveblocks-auth">
              <RoomProvider
                id="sidebar-room-2"
                initialStorage={{
                  initialMenuItems: INITIAL_MENU_ITEMS,
                  menuItems: INITIAL_MENU_ITEMS,
                }}
              >
                <Nav />

              <div className="flex h-full">
                <RoomProvider
                  id="sidebar-room-2"
                  initialStorage={{
                    initialMenuItems: INITIAL_MENU_ITEMS,
                    menuItems: INITIAL_MENU_ITEMS,
                  }}
                >
                  <div
                    style={{ width: isCollapsed ? COLLAPSED_LEFT_SIDEBAR_WIDTH : LEFT_SIDEBAR_WIDTH }}
                    className="h-inherit"
                  >
                    <LeftBar isCollapsed={isCollapsed} />
                  </div>
                </RoomProvider>

                <div className="flex-1">
                  <div className="flex flex-col md:flex-row" style={{ height: `calc(100vh - 72px)` }}>
                    <div
                      style={isVertical ? { height: `${leftSize}%` } : { width: `${leftSize}%` }}
                      className="h-full w-full overflow-y-auto"
                    >
                      <LeftMain />
                    </div>

                    <div
                      className="bg-gray-400 cursor-pointer"
                      style={{
                        width: isVertical ? '100%' : '5px',
                        height: isVertical ? '5px' : '100%',
                        cursor: isVertical ? 'row-resize' : 'col-resize',
                      }}
                      onMouseDown={handleMouseDown}
                    />

                    <div
                      style={isVertical ? { height: `${100 - leftSize}%` } : { width: `${100 - leftSize}%` }}
                      className="h-full w-full overflow-y-auto"
                    >
                      <RightMain />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </LiveblocksProvider>
        </MarkdownProvider>
      </MenuItemsProvider>
    </ActiveIdProvider>
  );
}
