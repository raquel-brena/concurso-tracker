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

  useEffect(() => {
    if (isPrivate) {
      navigate("/login");
    }
  }, [navigate]);

  return <>{children}</>;
};
