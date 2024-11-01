import { MenuItem } from './_types/navigation';

declare global {
  interface Liveblocks {
    Storage: {
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
