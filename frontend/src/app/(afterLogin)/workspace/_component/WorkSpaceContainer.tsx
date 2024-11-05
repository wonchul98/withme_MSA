'use client';

import { useQuery } from '@tanstack/react-query';
import { getPostWorkSpace } from '../page';
import { ApiResponse } from '../model/workSpaceItem'; // 타입 파일 경로에 맞게 수정
import UserWorkSpace from './UserWorkSpace';

export default function WorkSpaceContainer() {
  const { data: apiResponse } = useQuery<ApiResponse>({
    queryKey: ['workspace'],
    queryFn: getPostWorkSpace,
  });

  const workspaces = apiResponse?.data?.data?.content;

  if (!workspaces) {
    return <div></div>;
  }

  return (
    <>
      {workspaces.map((workspace) => (
        <UserWorkSpace key={workspace.id} workspace={workspace} /> // 각 workspace를 UserWorkSpace로 전달
      ))}
    </>
  );
}
