interface PageHeaderProps {
  title: string;
}

export default function PageHeader({ title }: PageHeaderProps) {
  return <div className="text-[40px] font-black">{title}</div>;
}
