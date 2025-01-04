import { Header } from "./components/header/Header";
import { Menubar } from "./components/MenuBarPrincipal/MenuBar";
import { Separator } from "./components/separator/separator";
import Login from "./modules/autenticacao/login/Login";

function App() {
  return (
    <div className="flex flex-col relative w-screen h-screen">
     <Login/>
    </div>
  );
}
export default App;
