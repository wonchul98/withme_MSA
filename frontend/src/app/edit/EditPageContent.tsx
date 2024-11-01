'use client';

import React from 'react';
import { useActiveId } from './_contexts/ActiveIdContext';
import { RoomProvider, ClientSideSuspense } from '@liveblocks/react/suspense';
import { Editor } from './_components/Editor';
import { LeftBar } from './_components/LeftBar';
import Nav from './_components/Nav';

function SidebarRoom() {
  const { activeId } = useActiveId();

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
      <div className="flex flex-col min-h-screen">
        <header className="w-full">
          <Nav />
        </header>
        <div className="flex flex-1">
          <div className="bg-gray-900">
            <LeftBar />
          </div>
          <div className="flex-1 p-4">
            <EditorRoom roomId={activeId} />
          </div>
        </div>
      </div>
    </RoomProvider>
  );
}

function EditorRoom({ roomId }: { roomId: null | number }) {
  return (
    <RoomProvider
      key={roomId}
      id={`room-${roomId}`}
      initialStorage={{
        menuItems: [],
      }}
    >
      <ClientSideSuspense fallback={<div>Loading...</div>}>
        <main>
          <Editor />
        </main>
      </ClientSideSuspense>
    </RoomProvider>
  );
}

export default function EditPageContent() {
  return (
    <div>
      <SidebarRoom />
    </div>
  );
}
