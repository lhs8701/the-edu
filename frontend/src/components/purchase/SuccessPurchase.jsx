import { CircularProgress } from "@mui/material";
import { useNavigate, useParams } from "react-router";

export default function SuccessPurchase() {
  const navigate = useNavigate();
  const orderId = new URL(window.location.href).searchParams.get("orderId");
  const paymentKey = new URL(window.location.href).searchParams.get(
    "paymentKey"
  );
  const amount = new URL(window.location.href).searchParams.get("amount");

  return (
    <div>
      성공
      <CircularProgress
        sx={{
          color: "var(--color-primary)",
        }}
      />
    </div>
  );
}
