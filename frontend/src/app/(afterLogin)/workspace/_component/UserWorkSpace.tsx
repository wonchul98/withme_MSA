'use client';
import { useEffect, useRef, useState } from 'react';
import Image from 'next/image';
import '@/app/globals.css';
import ViewIcon from './ViewIcon';
import EditIcon from './EditIcon';
import TrashIcon from './TrashIcon';

const UserWorkSpace: React.FC = () => {
  const [isVisible, setIsVisible] = useState(false);
  const ref = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const observer = new IntersectionObserver(
      ([entry]) => {
        if (entry.isIntersecting) {
          setIsVisible(true);
          observer.unobserve(entry.target);
        }
      },
      { threshold: 0.1 },
    );

    if (ref.current) observer.observe(ref.current);

    return () => observer.disconnect();
  }, []);

  return (
    <div
      ref={ref}
      style={{ borderRadius: '16px', border: '1px solid #CCCCCC', backgroundColor: '#CCCCCC' }}
      className="w-full relative rounded-[16px] overflow-hidden flex flex-col border-2 border-[#CCCCCC] aspect-[1/0.8]"
    >
      {!isVisible ? (
        <div style={{ width: '100%', height: '80%', backgroundColor: '#CCCCCC' }} />
      ) : (
        <Image
          className="workspace-image cursor-pointer"
          src="/baseImage.png"
          alt="Description of the image"
          width={200}
          height={300}
          sizes="100%"
          style={{ width: 'auto', height: '80%', objectFit: 'fill' }}
          onLoad={() => setIsVisible(true)}
        />
      )}

      <div className="workspace-item absolute flex flex-row">
        <div className="flex-1 flex justify-center items-center">
          <ViewIcon />
        </div>
        <div className="flex-1 flex justify-center items-center">
          <EditIcon />
        </div>
      </div>
      <div
        style={{ padding: '10px', height: '20%', backgroundColor: 'white' }}
        className="p-[10px] flex items-center h-[20%] gap-2"
      >
        <div
          style={{ fontSize: '16px' }}
          className="text-[16px] font-bold overflow-hidden whitespace-nowrap text-ellipsis flex-1"
        >
          여행지 추천 서비스 - TripMate
        </div>

        <div className="flex items-center justify-end">
          <TrashIcon />
        </div>
      </div>
    </div>
  );
};

export default UserWorkSpace;
