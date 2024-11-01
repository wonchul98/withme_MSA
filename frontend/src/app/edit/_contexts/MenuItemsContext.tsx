'use client';

import { createContext, useContext, useState } from 'react';
import { MenuItem } from '../_types/MenuItem';

type MenuItemsContextType = {
  menuItems: MenuItem[] | null;
  setMenuItems: (items: MenuItem[] | null) => void;
};

const MenuItemsContext = createContext<MenuItemsContextType | undefined>(undefined);

export function MenuItemsProvider({ children }: { children: React.ReactNode }) {
  const [items, setItems] = useState<MenuItem[] | null>(null);

  return (
    <MenuItemsContext.Provider value={{ menuItems: items, setMenuItems: setItems }}>
      {children}
    </MenuItemsContext.Provider>
  );
}

export function useMenuItems() {
  const context = useContext(MenuItemsContext);
  if (context === undefined) {
    throw new Error('useMenuItems must be used within a MenuItemsProvider');
  }
  return context;
}
