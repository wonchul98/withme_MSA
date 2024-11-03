import NoImage from './NoImage';

export default function CreateImage() {
  return (
    <div className="h-[330px] pt-[20px]  flex justify-center items-center flex-col">
      <div className="flex justify-center items-center flex-col bg-[#E9ECEF] h-[70%] w-full">
        <NoImage />
        <button className="p-[10px] pr-[30px] pl-[30px] mt-5 bg-[#020623] text-white font-bold rounded-lg transition duration-200 ease-in-out transform hover:bg-[#1B1F30] hover:scale-105 focus:outline-none focus:ring-2 focus:ring-[#1B1F30] focus:ring-opacity-50">
          썸네일 업로드
        </button>
      </div>
      <div className="h-[30%] flex flex-col justify-between">
        <div className="mt-[10px] font-bold">
          <label className="flex items-center">
            <input type="checkbox" className="mr-2 custom-checkbox " />
            AI 이미지를 생성하시겠습니까?
          </label>
        </div>

        <button className="p-[5px] bg-white text-black border-2 border-black mx-auto rounded-lg w-1/2 cursor-pointer transition-colors duration-200 hover:bg-[#020623] hover:text-white">
          Create
        </button>
      </div>
    </div>
  );
}
