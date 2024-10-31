import Image from 'next/image';
import LoginBtn from './LoginBtn';
import SearchBar from './SearchBar';

export default function Nav() {
  return (
    <nav className="bg-[#020623] w-full p-[12px] flex flex-row items-center">
      <div className="flex-1" style={{ flex: '1 1 0%' }}>
        <Image
          className="workspace-image cursor-pointer"
          src="/Logo.png"
          alt="logo of the image"
          width={200}
          height={43.05}
          sizes="100%"
          style={{ width: '200px', height: '100%' }}
        />
      </div>
      <div className="flex-2 ml-[10px]" style={{ flex: '2 1 0%' }}>
        <SearchBar />
      </div>
      <div className="flex-1" style={{ flex: '1 1 0%' }}>
        <LoginBtn />
      </div>
    </nav>
  );
}
