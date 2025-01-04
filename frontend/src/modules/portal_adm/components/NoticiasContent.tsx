import { Content } from "./Content"

export const NoticiasContent = () => { 
    return (
      <Content title="Notícias">
        <div className="w-full flex flex-col items-center h-screen pt-2">
          <div className="w-full flex items-center bg-[#4F0A05] h-[10%]">
            <p className="text-white ml-8 font-normal md:font-extralight md:text-xl">
              Acompanhe as últimas notícias e atualizações sobre concursos
            </p>
          </div>
          <div className="h-screen w-full flex justify-center items-center bg-[#652823]"></div>
        </div>
      </Content>
    );
}