import { FaChevronLeft } from 'react-icons/fa';

export default function FoldButton() {
  return (
    <button className="absolute top-[400px] left-[319px] h-[60px] w-[20px] rounded-r-lg bg-gray-900">
      <FaChevronLeft color="white" />
    </button>
  );
}
