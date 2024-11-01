'use client';

import React from 'react';
import { ActiveIdProvider } from './_contexts/ActiveIdContext';
import dynamic from 'next/dynamic';

const LiveblocksProviderWithNoSSR = dynamic(
  () => import('@liveblocks/react/suspense').then((mod) => mod.LiveblocksProvider),
  { ssr: false },
);

const EditPageContent = dynamic(() => import('./EditPageContent'), { ssr: false });

export default function EditPage() {
  return (
    <ActiveIdProvider>
      <LiveblocksProviderWithNoSSR authEndpoint="/api/liveblocks-auth">
        <EditPageContent />
      </LiveblocksProviderWithNoSSR>
    </ActiveIdProvider>
  );
}
