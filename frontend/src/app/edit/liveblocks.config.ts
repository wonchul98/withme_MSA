import { MenuItem } from './_types/MenuItem';

declare global {
  interface Liveblocks {
    Storage: {
      initialMenuItems: MenuItem[];
      menuItems: MenuItem[];
    };
    UserMeta: {
      id: string;
      info: {
        // Example properties, for useSelf, useUser, useOthers, etc.
        name: string;
        color: [string, string];
        avatar?: string;
      };
    };
  }
}

export {};
