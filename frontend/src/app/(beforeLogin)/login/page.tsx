'use client';

import { useRouter } from 'next/navigation';

export default function Login() {
  const router = useRouter();
  router.replace('/');
  return <>123</>;
}
