'use client';
import { useUserRepoQuery } from '@/stores/server/getUserRepoQuery';
import { useUserSyncQuery } from '@/stores/server/getUserSyncQuery';
import axios from 'axios';
import { useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';

export default function Home() {
  const router = useRouter();
  const { data: repoData, isLoading: repoLoading } = useUserRepoQuery();
  const { data: syncData, isLoading: syncLoading } = useUserSyncQuery();
  const [isReady, setIsReady] = useState(false);

  const handleCallback = async () => {
    const code = new URLSearchParams(window.location.search).get('code');
    const state = new URLSearchParams(window.location.search).get('state');
    if (!code || !state) return;

    try {
      const response = await axios.get(
        `${process.env.NEXT_PUBLIC_BACKEND_URL}/login/oauth2/code/github?code=${code}&state=${state}`,
        { headers: { 'Content-Type': 'application/json' } },
      );
      if (response.data) {
        document.cookie = `userData=${encodeURIComponent(JSON.stringify(response.data))}; path=/;`;
        router.push('/workspace');
      }
    } catch (error) {
      console.error('Error fetching access token:', error);
    }
  };

  useEffect(() => {
    // repo와 sync 데이터 로딩이 모두 완료된 후 콜백을 실행
    if (!repoLoading && !syncLoading) {
      setIsReady(true);
    }
  }, [repoLoading, syncLoading]);

  useEffect(() => {
    if (isReady) {
      handleCallback();
    }
  }, [isReady]);

  return (
    <div className="flex flex-col items-center justify-center h-screen bg-gray-100">
      <svg xmlns="http://www.w3.org/2000/svg" width="30%" height="30%" viewBox="0 0 24 24">
        <path d="M12 0c-6.626 0-12 5.373-12 12 0 5.302 3.438 9.8 8.207 11.387.599.111.793-.261.793-.577v-2.234c-3.338.726-4.033-1.416-4.033-1.416-.546-1.387-1.333-1.756-1.333-1.756-1.089-.745.083-.729.083-.729 1.205.084 1.839 1.237 1.839 1.237 1.07 1.834 2.807 1.304 3.492.997.107-.775.418-1.305.762-1.604-2.665-.305-5.467-1.334-5.467-5.931 0-1.311.469-2.381 1.236-3.221-.124-.303-.535-1.524.117-3.176 0 0 1.008-.322 3.301 1.23.957-.266 1.983-.399 3.003-.404 1.02.005 2.047.138 3.006.404 2.291-1.552 3.297-1.23 3.297-1.23.653 1.653.242 2.874.118 3.176.77.84 1.235 1.911 1.235 3.221 0 4.609-2.807 5.624-5.479 5.921.43.372.823 1.102.823 2.222v3.293c0 .319.192.694.801.576 4.765-1.589 8.199-6.086 8.199-11.386 0-6.627-5.373-12-12-12z" />
      </svg>
      <div className="w-24 h-24 border-8 border-gray-300 border-t-black rounded-full animate-spin mb-4 mt-4"></div>
      <p className="text-3xl font-bold text-black">로그인 중입니다...</p>
    </div>
  );
}
