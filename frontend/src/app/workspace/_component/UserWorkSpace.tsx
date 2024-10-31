import Image from 'next/image';
import '@/app/globals.css';
import ViewIcon from './ViewIcon';
import EditIcon from './EditIcon';
import TrashIcon from './TrashIcon';

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
        <div className="text-[16px] font-bold overflow-hidden whitespace-nowrap text-ellipsis flex-1">
          여행지 추천 서비스 - TripMate
        </div>

        <div className="flex items-center justify-end">
          <TrashIcon />
        </div>
      </div>
    </div>
  );
}
