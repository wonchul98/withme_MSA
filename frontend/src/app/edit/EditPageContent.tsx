'use client';

import React from 'react';
import { useActiveId } from './_contexts/ActiveIdContext';
import { RoomProvider, ClientSideSuspense } from '@liveblocks/react/suspense';
import { Editor } from './_components/Editor';
import { LeftBar } from './_components/LeftBar';
import LiveAvatars from './_components/LiveAvatars';

function SidebarRoom() {
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
        <LiveAvatars />
        <main className="h-full w-full p-4">
          <Editor />
        </main>
      </ClientSideSuspense>
    </RoomProvider>
  );
}

export default function EditPageContent() {
  const { activeId } = useActiveId();

  return (
    <div className="flex min-h-screen">
      <div className="fixed h-full">
        <SidebarRoom />
      </div>
      <div className="flex-1 ml-64">
        <EditorRoom roomId={activeId ? activeId : 1} />
        <div>{activeId}</div>
      </div>
    </div>
  );
}
