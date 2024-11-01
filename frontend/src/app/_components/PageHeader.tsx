interface PageHeaderProps {
  title: string;
}

export default function PageHeader({ title }: PageHeaderProps) {
  return (
    <div
      style={{
        fontSize: '40px', // text-[40px]
        fontWeight: '900', // font-black
      }}
    >
      {title}
    </div>
  );
}
