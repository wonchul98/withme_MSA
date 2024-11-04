'use client';

import { ActiveIdProvider, useActiveId } from './_contexts/ActiveIdContext';
import { MenuItemsProvider, useMenuItems } from './_contexts/MenuItemsContext';
import { Editor } from './_components/Editor';
import { LeftBar } from './_components/LeftBar';
import { LiveblocksProvider, RoomProvider, ClientSideSuspense } from '@liveblocks/react/suspense';
import { v4 as uuidv4 } from 'uuid';
import Nav from './_components/Nav';
import { MarkdownView } from './_components/MarkdownView';
import { MarkdownProvider } from './_contexts/MarkdownContext';
import { MarkdownPreview } from './_components/MarkdownPreview';

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

function LeftSidebar() {
  return (
    <div className="bg-gray-900 h-full">
      <LeftBar />
    </div>
  );
}
function LeftMain() {
  const { initialItems } = useMenuItems();

  // initialItems가 없거나 빈 배열이면 로딩 상태 표시
  if (!initialItems || initialItems.length === 0) {
    return (
      <div className="w-full h-full flex items-center justify-center">
        <div className="text-gray-400">Loading...</div>
      </div>
    );
  }

  return (
    <ClientSideSuspense fallback={<div>Loading...</div>}>
      <div className="relative h-full w-full">
        {initialItems.map((item) => (
          <RoomWithEditor key={`room-${item.id}`} id={item.id} />
        ))}
      </div>
    </ClientSideSuspense>
  );
}
function RightMain() {
  return (
    <div>
      <MarkdownView />
      <MarkdownPreview />
    </div>
  );
}

function RoomWithEditor({ id }: { id: string }) {
  const { activeId } = useActiveId();
  return (
    <RoomProvider
      id={`room-${id}`}
      initialStorage={{
        initialMenuItems: [],
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
        <MarkdownProvider>
          <LiveblocksProvider authEndpoint="/api/liveblocks-auth">
            <RoomProvider
              id="sidebar-room-2"
              initialStorage={{
                initialMenuItems: INITIAL_MENU_ITEMS,
                menuItems: INITIAL_MENU_ITEMS,
              }}
            >
              <Nav />

              <div className="flex">
                <div className="fixed h-full">
                  <LeftSidebar />
                </div>
              </div>
            </RoomProvider>
            <div className="flex-1 ml-64">
              <div className="flex">
                <div className="w-1/2">
                  <LeftMain />
                </div>
                <div className="w-1/2">
                  <RightMain />
                </div>
              </div>
            </div>
          </LiveblocksProvider>
        </MarkdownProvider>
      </MenuItemsProvider>
    </ActiveIdProvider>
  );
}
