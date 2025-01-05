import { ContentButton } from "../../components/buttons/ContentButton";
import { ChamadasContent } from "./components/ChamadasContent";
import { Content } from "./components/Content";
import { NoticiasContent } from "./components/NoticiasContent";

const buttons = [
  "Equipe",
  "Editais",
  "Vagas",
  "Candidatos",
  "Convocações",
  "Configurações",
];


export const Home = () => { 
    return (
      <>
        <div className="flex h-44 w-screen bg-[#FCF7F5] -z-10">.</div>
        {/* <Header
            title="Gerenciar processos seletivos"
            subtitle="GERENCIAR NOTÍCIAS"
            description="Gerencie todos os concursos cadastrados no sistema, você pode
            visualizar, editar e organizar as informações de cada concurso."
          /> */}

        <Content title="Gerenciar" className="gap-8 pb-4 my-2">
          <div
            className="grid md:grid-cols-3 md:grid-rows-2
             grid-cols-2 grid-rows-3 gap-8 md:w-fit w-full justify-center items-center px-4 md:px-0"
          >
            {buttons.map((buttonTitle) => (
              <ContentButton title={buttonTitle} />
            ))}
          </div>
        </Content>

        {/* <Content title="Gereciamento rápido" className="gap-8 pb-4 my-2">
          <div
            className="grid md:grid-cols-3 md:grid-rows-2 
            grid-cols-2 grid-rows-3 gap-8 md:w-fit w-full justify-center items-center px-4 md:px-0"
          >
            {buttons.map((buttonTitle) => (
              <ContentButton title={buttonTitle} />
            ))}
          </div>
        </Content> */}

        <NoticiasContent />

        <ChamadasContent />
      </>
    );
}