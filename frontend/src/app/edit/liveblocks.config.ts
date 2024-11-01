import { MenuItem } from './_types/MenuItem';

declare global {
  interface Liveblocks {
    Storage: {
      menuItems: MenuItem[];
    };
  }
}

export {};
