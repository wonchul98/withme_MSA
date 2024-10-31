import { MenuItem } from './_types/navigation';

declare global {
  interface Liveblocks {
    Storage: {
      menuItems: MenuItem[];
    };
  }
}

export {};
