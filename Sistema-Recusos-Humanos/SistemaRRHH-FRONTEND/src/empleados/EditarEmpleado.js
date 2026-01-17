import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'

export default function EditarEmpleado() {
   
    const urlBase = 'http://localhost:8080/rrhh-app/empleados'
    
    let navegacion = useNavigate();

    const {id} = useParams();
    
    const [empleado, setEmpleado] = useState({
        nombre: '',
        departamento: '',
        salario: '',
    })

    const { nombre, departamento, salario } = empleado

    useEffect(() => {
        cargarEmpleado();
    }, []);

    const cargarEmpleado = async () => {
        const resultado = await axios.get(`${urlBase}/${id}`);
        setEmpleado(resultado.data);
    }

    const oninputChange = (e) => {
        setEmpleado({ ...empleado, [e.target.name]: e.target.value })
    }
    
    const onSubmit = async (e) => {
        e.preventDefault();
        await axios.put(`${urlBase}/${id}`, empleado);
        navegacion('/');

    }

    return (
        <div className='container'>
            <div className="container text-center" style={{ margin: '30px' }}>
                <h3>Editar Empleado</h3>
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
                    <button type="agregar" className="btn btn-warning btn-sm me-3">Guardar</button>
                    <a href='/' className="btn btn-danger btn-sm">Regresar</a>
                </div>
            </form>

        </div>
    )

}
