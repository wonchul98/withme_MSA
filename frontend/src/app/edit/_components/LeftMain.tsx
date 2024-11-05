import { useActiveId } from '../_contexts/ActiveIdContext';
import { useMenuItems } from '../_contexts/MenuItemsContext';
import { RoomProvider, ClientSideSuspense } from '@liveblocks/react/suspense';
import { Editor } from './Editor';

export default function LeftMain() {
  const { initialItems } = useMenuItems();

  if (!initialItems || initialItems.length === 0) {
    return (
      <div className="w-full h-full flex items-center justify-center">
        <div className="text-gray-400">Loading...</div>
      </div>
    );
  }

  return (
    <ClientSideSuspense fallback={<div>Loading...</div>}>
      <div className="bg-white h-inherit w-full">
        {initialItems.map((item) => (
          <RoomWithEditor key={`room-${item.id}`} id={item.id} />
        ))}
      </div>
    </ClientSideSuspense>
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
        <div className={`p-4 transition-opacity duration-300 ${activeId === id ? '' : 'hidden pointer-events-none'}`}>
          <Editor />
        </div>
      </ClientSideSuspense>
    </RoomProvider>
  );
}
