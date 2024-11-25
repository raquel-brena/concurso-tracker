import { Header } from "./components/header/Header";
import MenubarDemo from "./components/MenuBarPrincipal/MenuBar";
import { Separator } from "./components/separator/separator";

function App() {
  return (
    <div className="flex flex-col relative w-screen h-screen">
      <MenubarDemo />
      <div className="flex h-1/4 w-full bg-[#FCF7F5]">.</div>
      <Header />
      <Separator />
    </div>
  );
}
export default App;
