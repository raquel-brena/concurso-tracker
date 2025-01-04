type ContentButtonProps = {
    title: string;
}

export const ContentButton = ({ title }: ContentButtonProps) => {
  return (
    <button className="bg-[#f8f8f8] md:text-sm h-fit md:w-fit md:min-w-72 w-full
    shadow-md  py-12 transition-all duration-200 hover:text-slate-950 
    hover:font-medium hover:shadow-lg">
      {title}
    </button>
  );
};