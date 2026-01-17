import { BrowserRouter, Route, Routes } from "react-router-dom";
import ListadoEmpleados from "./empleados/ListadoEmpleados";
import Navegacion from "./plantilla/Navegacion";
import AgregarEmpleados from "./empleados/AgregarEmpleados";
import EditarEmpleado from "./empleados/EditarEmpleado";


function App() {
  return (
    <div className="container">
      <BrowserRouter>
        <Navegacion />
        <Routes >
          <Route exact path="/" element={<ListadoEmpleados />} />
          <Route exact path="/agregar" element={<AgregarEmpleados />} />
          <Route exact path="/editar/:id" element={<EditarEmpleado />} />
        </Routes>
      </BrowserRouter>
    </div>

  );
}

export default App;
