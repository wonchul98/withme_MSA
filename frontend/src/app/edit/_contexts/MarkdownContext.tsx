'use client';

import { createContext, useContext, useState } from 'react';
import { Markdown } from '../_types/Markdown';

type MarkdownContextType = {
  markdowns: Markdown[] | null;
  setMarkdowns: (items: Markdown[] | null) => void;
};

const MarkdownContext = createContext<MarkdownContextType | undefined>(undefined);

export function MarkdownProvider({ children }: { children: React.ReactNode }) {
  const [markdowns, setMarkdowns] = useState<Markdown[] | null>(null);

  return (
    <MarkdownContext.Provider
      value={{
        markdowns,
        setMarkdowns,
      }}
    >
      {children}
    </MarkdownContext.Provider>
  );
}

export function useMarkdown() {
  const context = useContext(MarkdownContext);
  if (context === undefined) {
    throw new Error('useMarkdown must be used within a MarkdownProvider');
  }
  return context;
}
