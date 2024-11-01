'use client';

import { useState, useEffect } from 'react';
import { useStorage, useMutation } from '@liveblocks/react/suspense';
import { ClientSideSuspense } from '@liveblocks/react/suspense';
import { MenuItem } from '../_types/MenuItem';
import { EditIcon } from '../_icons/EditIcon';
import { useActiveId } from '../_contexts/ActiveIdContext';
import { useMenuItems } from '../_contexts/MenuItemsContext';

function LeftBarContent() {
  const { activeId, setActiveId } = useActiveId();
  const { menuItems, setMenuItems } = useMenuItems();
  const [draggedItem, setDraggedItem] = useState<MenuItem | null>(null);
  const [editingId, setEditingId] = useState<number | null>(null);
  const [editValue, setEditValue] = useState<string>('');

  const storageMenuItems = useStorage((root) => root.menuItems);

  useEffect(() => {
    setMenuItems(storageMenuItems);
  }, [storageMenuItems, setMenuItems]);

  useEffect(() => {
    if (menuItems && menuItems.length > 0 && activeId === null) {
      setActiveId(menuItems[0].id);
    }
  }, [menuItems, activeId, setActiveId]);

  const updateItemLabel = useMutation(({ storage }, { id, newLabel }: { id: number; newLabel: string }) => {
    const menuItems = storage.get('menuItems');
    const updatedMenuItems = menuItems.map((item: MenuItem) => (item.id === id ? { ...item, label: newLabel } : item));
    storage.set('menuItems', updatedMenuItems);
    setMenuItems(updatedMenuItems);
  }, []);

  const handleEditClick = (e: React.MouseEvent, item: MenuItem) => {
    e.stopPropagation();
    setEditingId(item.id);
    setEditValue(item.label);
  };

  const handleEditSubmit = (id: number) => {
    if (editValue.trim()) {
      updateItemLabel({ id, newLabel: editValue.trim() });
    }
    setEditingId(null);
    setEditValue('');
  };

  const handleDragStart = (e: React.DragEvent<HTMLDivElement>, item: MenuItem) => {
    setDraggedItem(item);
    e.currentTarget.style.opacity = '0.4';
  };

  const handleDragEnd = (e: React.DragEvent<HTMLDivElement>) => {
    e.currentTarget.style.opacity = '1';
    setDraggedItem(null);
  };

  const handleDragOver = (e: React.DragEvent) => {
    e.preventDefault();
  };

  const handleDrop = useMutation(
    ({ storage }, { draggedItem, targetItem }: { draggedItem: MenuItem; targetItem: MenuItem }) => {
      const menuItems = storage.get('menuItems');
      const updatedItems = [...menuItems];
      const draggedIndex = updatedItems.findIndex((item: MenuItem) => item.id === draggedItem.id);
      const targetIndex = updatedItems.findIndex((item: MenuItem) => item.id === targetItem.id);

      if (draggedIndex !== -1 && targetIndex !== -1) {
        const [removed] = updatedItems.splice(draggedIndex, 1);
        updatedItems.splice(targetIndex, 0, removed);
        storage.set('menuItems', updatedItems);
        setMenuItems(updatedItems);
      }
    },
    [],
  );

  if (!menuItems) return null;

  return (
    <div className="h-full p-4 space-y-2">
      {menuItems.map((item: MenuItem) => (
        <div
          key={item.id}
          draggable={editingId !== item.id}
          onDragStart={(e) => handleDragStart(e, item)}
          onDragEnd={handleDragEnd}
          onDragOver={handleDragOver}
          onDrop={(e) => {
            e.preventDefault();
            if (!draggedItem) return;
            handleDrop({ draggedItem, targetItem: item });
          }}
          className={`
            w-full px-4 py-3 rounded-lg
            transition-colors duration-200
            text-sm font-medium
            group
            ${editingId !== item.id ? 'cursor-grab active:cursor-grabbing' : ''}
            ${
              activeId === item.id
                ? 'bg-white bg-opacity-10 text-white'
                : 'text-gray-400 hover:bg-white hover:bg-opacity-5 hover:text-gray-200'
            }
            flex items-center justify-between
          `}
          onClick={() => editingId !== item.id && setActiveId(item.id)}
        >
          {editingId === item.id ? (
            <input
              type="text"
              value={editValue}
              onChange={(e) => setEditValue(e.target.value)}
              onBlur={() => handleEditSubmit(item.id)}
              onKeyDown={(e) => {
                if (e.key === 'Enter') handleEditSubmit(item.id);
                if (e.key === 'Escape') {
                  setEditingId(null);
                  setEditValue('');
                }
              }}
              className="bg-transparent border-none outline-none flex-1 text-white"
              autoFocus
            />
          ) : (
            <>
              <span>{item.label}</span>
              <button
                onClick={(e) => handleEditClick(e, item)}
                className="opacity-0 group-hover:opacity-100 transition-opacity ml-2"
              >
                <EditIcon />
              </button>
            </>
          )}
        </div>
      ))}
    </div>
  );
}

export function LeftBar() {
  return (
    <ClientSideSuspense fallback={<div>Loading...</div>}>
      {() => (
        <div className="flex min-h-screen">
          <div className="fixed left-0 top-0 h-screen bg-gray-900 w-64 border-r border-gray-800">
            <LeftBarContent />
          </div>
        </div>
      )}
    </ClientSideSuspense>
  );
}
