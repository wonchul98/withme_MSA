import UserWorkSpace from './_component/UserWorkSpace';
import '@/app/globals.css';
import PageHeader from '../_components/PageHeader';
import { PAGE_HEADER } from '../_util/constants';

export default function Home() {
  return (
    <div className="responsive_mainResponsive">
      <main className="w-full ">
        <header style={{ paddingTop: '30px', paddingBottom: '30px' }} className="pt-[30px] pb-[30px]">
          <PageHeader title={PAGE_HEADER.workspace} />
        </header>
        <div className="grid_mainGrid ">
          <UserWorkSpace />
        </div>
      </main>
    </div>
  );
}
