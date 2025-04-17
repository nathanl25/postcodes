import { BrowserRouter, Route, Routes } from 'react-router';
import HomePage from './pages/HomePage/HomePage';
import AdminPage from './pages/AdminPage/AdminPage';
import LoginPage from './pages/LoginPage/LoginPage';
import LoginContextProvider from './context/LoginContextProvider';
import PrivateRoute from './routes/PrivateRoute';
import PostcodeContextProvider from './context/PostcodeContextProvider';
import AlterPage from './pages/AlterPage/AlterPage';
import ViewPage from './pages/ViewPage/ViewPage';
import HeaderContextProvider from './context/HeaderContextProvider';
import Header from './components/Header/Header';

function App() {
  return (
    <HeaderContextProvider>
      <PostcodeContextProvider>
        <LoginContextProvider>
          <BrowserRouter>
            <Header />
            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route
                path="/admin"
                element={
                  <PrivateRoute>
                    <AdminPage />
                  </PrivateRoute>
                }
              />
              <Route
                path="/edit"
                element={
                  <PrivateRoute>
                    <AlterPage />
                  </PrivateRoute>
                }
              />
              <Route
                path="/create"
                element={
                  <PrivateRoute>
                    <AlterPage />
                  </PrivateRoute>
                }
              />
              <Route path="/view" element={<ViewPage />} />
              <Route path="/login" element={<LoginPage />} />
            </Routes>
          </BrowserRouter>
        </LoginContextProvider>
      </PostcodeContextProvider>
    </HeaderContextProvider>
  );
}

export default App;
