import { useNavigate } from 'react-router-dom'

const Login = () => {
    const navigate = useNavigate()
    
    const handleLogin = () => {
        navigate('/dashboard')
    }
    
    return (
        <div>
        <h1>Login</h1>
        <button onClick={handleLogin}>Login</button>
        </div>
    )
}

export default Login;