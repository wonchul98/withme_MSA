'use client';

import { useRef } from 'react';
import { ActiveIdProvider, useActiveId } from './_contexts/ActiveIdContext';
import { MenuItemsProvider, useMenuItems } from './_contexts/MenuItemsContext';
import { Editor } from './_components/Editor';
import { LeftBar } from './_components/LeftBar';
import { LiveblocksProvider, RoomProvider, ClientSideSuspense } from '@liveblocks/react/suspense';

function LeftSidebar() {
  return (
    <RoomProvider
      id="sidebar-room"
      initialStorage={{
        menuItems: [
          { id: 1, label: 'Dashboard' },
          { id: 2, label: 'Projects' },
          { id: 3, label: 'Tasks' },
          { id: 4, label: 'Messages' },
          { id: 5, label: 'Settings' },
        ],
      }}
    >
      <div className="bg-gray-900">
        <LeftBar />
      </div>
    </RoomProvider>
  );
}
function LeftMain() {
  const { menuItems } = useMenuItems();

  const editorIdsRef = useRef<Set<number> | null>(null);

  if (!editorIdsRef.current && menuItems) {
    editorIdsRef.current = new Set(menuItems.map((item) => item.id));
  }

  if (!menuItems || !editorIdsRef.current) return null;

  return (
    <ClientSideSuspense fallback={<div>Loading...</div>}>
      <div className="relative h-full w-full">
        {Array.from(editorIdsRef.current).map((id) => (
          <RoomWithEditor key={`room-${id}`} id={id} />
        ))}
      </div>
    </ClientSideSuspense>
  );
}

function RoomWithEditor({ id }: { id: number }) {
  const { activeId } = useActiveId();
  return (
    <RoomProvider
      id={`room-${id}`}
      initialStorage={{
        menuItems: [],
      }}
    >
      <ClientSideSuspense fallback={<div>Loading...</div>}>
        <div
          className={`absolute inset-0 p-4 transition-opacity duration-300 ${
            activeId === id ? 'opacity-100 z-10' : 'opacity-0 z-0 pointer-events-none'
          }`}
        >
          <Editor />
        </div>
      </ClientSideSuspense>
    </RoomProvider>
  );
}

export default function EditPage() {
  return (
    <ActiveIdProvider>
      <MenuItemsProvider>
        <LiveblocksProvider publicApiKey="pk_dev_NrnSHcf8kdqt92bL0XiFaOWgfuIDEx7fzLUimbyv02xZ8vM_NSxIdwQ8KHPK_aNd">
          <div className="flex min-h-screen">
            <div className="fixed h-full">
              <LeftSidebar />
            </div>
            <div className="flex-1 ml-64">
              <LeftMain />
            </div>
          </div>
        </LiveblocksProvider>
      </MenuItemsProvider>
    </ActiveIdProvider>
  );
}
