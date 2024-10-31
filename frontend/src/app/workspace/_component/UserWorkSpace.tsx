import Image from 'next/image';
import '@/app/globals.css';
import ViewIcon from './ViewIcon';
import EditIcon from './EditIcon';

export default function UserWorkSpace() {
  return (
    <div className="w-full relative rounded-[16px] overflow-hidden flex flex-col  border-2 border-[#CCCCCC] aspect-[1/0.8]">
      <Image
        className="workspace-image cursor-pointer "
        src="/baseImage.png"
        alt="Description of the image"
        width={0}
        height={0}
        sizes="100%"
        style={{ width: 'auto', height: '80%', objectFit: 'fill' }}
      />

      <div className="workspace-item absolute flex flex-row">
        <div className="flex-1 flex justify-center items-center">
          <ViewIcon />
        </div>
        <div className="flex-1 flex justify-center items-center">
          <EditIcon />
        </div>
      </div>
      <div className="p-[10px] flex items-center h-[20%]  gap-2">
        <div className="text-[16px] font-bold overflow-hidden whitespace-nowrap text-ellipsis">
          여행지 추천 서비스 - TripMate
        </div>

        <svg
          width="30px"
          height="30px"
          viewBox="0 0 24 24"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
          className="cursor-pointer"
        >
          <path
            d="M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17"
            stroke="#000000"
            strokeWidth="2"
            strokeLinecap="round"
            strokeLinejoin="round"
          />
        </svg>
      </div>
    </div>
  );
}
