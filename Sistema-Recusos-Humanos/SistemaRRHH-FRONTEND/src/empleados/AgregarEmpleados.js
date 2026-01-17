import axios from 'axios'
import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'

export default function AgregarEmpleados() {
    let navegacion = useNavigate();
    const [empleado, setEmpleado] = useState({
        nombre: '',
        departamento: '',
        salario: '',
    })
    const { nombre, departamento, salario } = empleado

    const oninputChange = (e) => {
        setEmpleado({ ...empleado, [e.target.name]: e.target.value })
    }
    const onSubmit = async (e) => {
        e.preventDefault();
        const urlBase = 'http://localhost:8080/rrhh-app/empleados'
        await axios.post(urlBase, empleado);
        navegacion('/');

    }


    return (
        <div className='container'>
            <div className="container text-center" style={{ margin: '30px' }}>
                <h3>Agregar Empleado</h3>
            </div>
            <form onSubmit={(e) => onSubmit(e)}>
                <div className="mb-3">
                    <label htmlFor="nombre" className="form-label">Nombre y Apellido</label>
                    <input type="text" className="form-control" id="nombre" name='nombre' required={true}
                        value={nombre} onChange={(e) => oninputChange(e)} />

                </div>
                <div className="mb-3">
                    <label htmlFor="departamento" className="form-label">Departamento</label>
                    <input type="text" className="form-control" id="departamento" name='departamento'
                        value={departamento} onChange={(e) => oninputChange(e)} />
                </div>
                <div className="mb-3">
                    <label htmlFor="salario" className="form-label">Sueldo</label>
                    <input type="number" step="any" className="form-control" id="salario" name='salario'
                        value={salario} onChange={(e) => oninputChange(e)} />
                </div>
                <div className="mb-3 form-check">
                    <input type="checkbox" className="form-check-input" id="exampleCheck1" />
                    <label className="form-check-label" htmlFor="exampleCheck1"></label>
                </div>
                <div className='text-center'>
                    <button type="agregar" className="btn btn-warning btn-sm me-3">Agregar</button>
                    <a href='/' className="btn btn-danger btn-sm">Regresar</a>
                </div>
            </form>

        </div>
    )

}
