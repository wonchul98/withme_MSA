import { MenuItem } from './_types/MenuItem';

declare global {
  interface Liveblocks {
    Storage: {
      initialMenuItems: MenuItem[];
      menuItems: MenuItem[];
    };
  }
}

export {};
