'use client';
import axios from 'axios';
import { useRouter } from 'next/navigation';
import { useEffect } from 'react';

export default function Home() {
  const router = useRouter();

  const handleCallback = async () => {
    const code = getQueryParams('code');
    const state = getQueryParams('state');
    if (!code || !state) return;

    try {
      const response = await axios.get(
        `${process.env.NEXT_PUBLIC_BACKEND_URL}/login/oauth2/code/github?code=${code}&state=${state}`,

        {
          headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': `application/json`,
            'ngrok-skip-browser-warning': '69420',
          },
        },
      );

      if (response.data) {
        // 응답 데이터를 쿠키에 저장
        const serializedData = JSON.stringify(response.data);
        document.cookie = `userData=${encodeURIComponent(serializedData)}; path=/;`; // 1시간 동안 유효
        router.push(`/workspace`);
      }
    } catch (error) {
      console.error('Error fetching access token:', error);
    }
  };

  const getQueryParams = (keyword: string) => {
    const params = new URLSearchParams(window.location.search);
    return params.get(keyword);
  };

  useEffect(() => {
    handleCallback();
  }, []);
}
