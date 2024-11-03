import UserWorkSpace from './_component/UserWorkSpace';
import '@/app/globals.css';
import PageHeader from '../_components/PageHeader';
import { PAGE_HEADER } from '../_util/constants';
import RepoBtn from './_component/RepoBtn';
import SyncBtn from './_component/SyncBtn';
export default function Home() {
  return (
    <div className="responsive_mainResponsive">
      <main className="w-full ">
        <header style={{ paddingTop: '30px', paddingBottom: '30px' }} className="responsive_workspaceheader">
          <PageHeader title={PAGE_HEADER.workspace} />
          <div className="flex justify-end h-full gap-3 w-full">
            <SyncBtn />
            <RepoBtn />
          </div>
        </header>
        <div className="grid_mainGrid ">
          <UserWorkSpace />
        </div>
      </main>
    </div>
  );
}
