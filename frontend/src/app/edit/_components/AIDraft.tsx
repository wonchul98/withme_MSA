import { useState, useEffect } from 'react';

import { useActiveId } from '../_contexts/ActiveIdContext';
import { useMenuItems } from '../_contexts/MenuItemsContext';

export function AIDraft() {
  const { activeId } = useActiveId();
  const { menuItems } = useMenuItems();
  const [activeLabel, setActiveLabel] = useState<string>();
  const [promptValue, setPromptValue] = useState<string>('');

  useEffect(() => {
    const activeMenuItem = menuItems.find((item) => item.id === activeId);
    setActiveLabel(activeMenuItem ? activeMenuItem.label : '');
  }, [menuItems, activeId]);

  const handleInputChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setPromptValue(event.target.value);
  };

  const handleSubmit = () => {
    console.log('입력된 프롬프트:', promptValue);
    setPromptValue(''); // 전송 후 입력창 초기화
  };
  console.log(menuItems);

  useEffect(() => {});
  return (
    <div className="">
      <div>현재 목차: {activeLabel}</div>
      <div className="mt-4">
        <textarea
          value={promptValue}
          onChange={handleInputChange}
          placeholder="여기에 프롬프트를 입력하세요..."
          className="w-full p-2 border border-gray-300 rounded-md h-[100px] resize-none"
        />
        <button onClick={handleSubmit} className="mt-2 px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700">
          전송
        </button>
      </div>
      <div className="mt-2">입력된 프롬프트: {promptValue}</div>
    </div>
  );
}
