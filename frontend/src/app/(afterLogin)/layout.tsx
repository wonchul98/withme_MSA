import { ReactNode } from 'react';
import Nav from '../_components/Nav';
import RQProvider from './_components/RQProvider';
type Props = { children: ReactNode; modal: ReactNode };
export default function Layout({ children }: Props) {
  return (
    <div className="container-wrapper">
      <RQProvider>
        <Nav />
        <div className="container-block"> </div>
        {children}
      </RQProvider>
    </div>
  );
}
