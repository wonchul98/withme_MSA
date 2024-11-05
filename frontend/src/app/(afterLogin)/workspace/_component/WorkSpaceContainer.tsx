'use client';

import { useQuery } from '@tanstack/react-query';
import { getPostWorkSpace } from '../page';

export default function WorkSpaceContainer() {
  const { data } = useQuery({ queryKey: ['workspace'], queryFn: getPostWorkSpace });
  console.log(data);
  return <>{JSON.stringify(data)}</>;
}
