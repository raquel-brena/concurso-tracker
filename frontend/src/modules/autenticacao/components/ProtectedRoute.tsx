import { PropsWithChildren, useEffect } from "react";
import { useNavigate } from "react-router-dom";

type ProtectedRouteProps = PropsWithChildren & {
  isPrivate?: boolean;
};
export const ProtectedRoute = ({
  children,
  isPrivate,
}: ProtectedRouteProps) => {
  const navigate = useNavigate();

  const { token } = localStorage;
  useEffect(() => {
    if (isPrivate && !token) {
      navigate("/login");
    }
  }, [navigate]);

  return <>{children}</>;
};
