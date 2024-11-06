// import UserWorkSpace from './_component/UserWorkSpace';
'use server';

import '@/app/globals.css';
import PageHeader from '../../_components/PageHeader';
import { PAGE_HEADER } from '@/util/constants';
import RepoBtn from './_component/RepoBtn';
import SyncBtn from './_component/SyncBtn';
import { dehydrate, HydrationBoundary, QueryClient } from '@tanstack/react-query';
import axios from '@/util/axiosConfig';
import { API_URL } from '@/util/constants';
import WorkSpaceContainer from './_component/WorkSpaceContainer';

interface PageParam {
  page: number;
  cursor: string | null;
}

export async function getPostWorkSpace({ pageParam = { page: 0, cursor: null } }: { pageParam?: PageParam }) {
  try {
    const response = await axios.get(`${process.env.NEXT_PUBLIC_BACKEND_URL}${API_URL.WORKSPACE_O}`, {
      headers: {
        'Cache-Control': 'no-store',
      },
      params: {
        cursor: pageParam.cursor,
        page: pageParam.page,
        size: 10,
      },
    });

    return {
      data: response.data,
    };
  } catch (error) {
    throw new Error('Failed to fetch data: ' + error);
  }
}

export default async function Home() {
  const queryClient: QueryClient = new QueryClient();
  await queryClient.prefetchInfiniteQuery({
    queryKey: ['workspace'],
    queryFn: ({ pageParam = { page: 0, cursor: null } }: { pageParam: PageParam }) => getPostWorkSpace({ pageParam }),
    initialPageParam: { page: 0, cursor: null } as PageParam,
  });
  const dehydratedState = dehydrate(queryClient);

  return (
    <div className="responsive_mainResponsive">
      <HydrationBoundary state={dehydratedState}>
        <main className="w-full ">
          <header style={{ paddingTop: '30px', paddingBottom: '30px' }} className="responsive_workspaceheader">
            <PageHeader title={PAGE_HEADER.workspace} />
            <div className="flex justify-end h-full gap-3 w-full">
              <SyncBtn />
              <RepoBtn />
            </div>
          </header>
          <div className="grid_mainGrid ">
            <WorkSpaceContainer />
          </div>
        </main>
      </HydrationBoundary>
    </div>
  );
}
