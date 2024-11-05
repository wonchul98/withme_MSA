// import UserWorkSpace from './_component/UserWorkSpace';
'use server';

import '@/app/globals.css';
import PageHeader from '../../_components/PageHeader';
import { PAGE_HEADER } from '../../../util/constants';
import RepoBtn from './_component/RepoBtn';
import SyncBtn from './_component/SyncBtn';
import { dehydrate, HydrationBoundary, QueryClient } from '@tanstack/react-query';
import axios from '@/util/axiosConfig';
import { API_URL } from '../../../util/constants';
import WorkSpaceContainer from './_component/WorkSpaceContainer';

export async function getPostWorkSpace() {
  try {
    const response = await axios.get(`${process.env.NEXT_PUBLIC_BACKEND_URL}${API_URL.WORKSPACE_O}`, {
      headers: {
        'Cache-Control': 'no-store',
      },
    });

    return {
      data: response.data,
    };
  } catch (error) {
    // 에러 처리
    throw new Error('Failed to fetch data: ' + error);
  }
}

// export async function getPostWorkSpace() {
//   try {
//     const response = await axios.get(`${process.env.NEXT_PUBLIC_BACKEND_URL}${API_URL.WORKSPACE_O}`, {
//       headers: {
//         'Cache-Control': 'no-store',
//       },
//       withCredentials: true, // 쿠키를 포함하여 요청
//     });

//     return response.data;
//   } catch (error) {
//     // 에러 처리
//     throw new Error('Failed to fetch data: ' + error);
//   }
// }

export default async function Home() {
  const queryClient: QueryClient = new QueryClient();
  await queryClient.prefetchQuery({ queryKey: ['workspace'], queryFn: getPostWorkSpace });
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
