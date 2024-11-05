'use client';

import { createContext, useContext, useRef, useEffect } from 'react';
import { Editor } from '../_types/Editor';
import { useMenuItems } from '../_contexts/MenuItemsContext';

type EditorContextType = {
  editorsRef: React.MutableRefObject<Editor[] | null>;
  updateEditorsEditableState: (targetId: string) => void;
};

const EditorContext = createContext<EditorContextType | undefined>(undefined);

export function EditorProvider({ children }: { children: React.ReactNode }) {
  const editorsRef = useRef<Editor[] | null>(null);
  const { menuItems } = useMenuItems();

  const updateEditorsEditableState = (targetId: string) => {
    if (editorsRef.current) {
      editorsRef.current.forEach((item) => {
        if (item.editor) {
          item.editor.isEditable = item.id === targetId;
        }
      });
    }
  };

  useEffect(() => {
    if (editorsRef !== null) {
      editorsRef.current = menuItems.map((menuItem) => ({
        id: menuItem.id,
        editor: null,
      }));
    }
  }, [menuItems]);

  return (
    <EditorContext.Provider
      value={{
        editorsRef,
        updateEditorsEditableState,
      }}
    >
      {children}
    </EditorContext.Provider>
  );
}

export function useEditor() {
  const context = useContext(EditorContext);
  if (context === undefined) {
    throw new Error('useEditor must be used within a EditorProvider');
  }
  return context;
}
