import { fetchCoins } from "../../api/socialAuthApi";
import { useQuery } from "react-query";
import { Suspense, useEffect } from "react";

export default function KaKaoAuth() {
  const { status, data, error, isIdle } = useQuery("test", fetchCoins, {
    suspense: true,
  });

  console.log(data);
  console.log(isIdle);

  return (
    <Suspense fallback={<div>loading</div>}>
      <div>실험중</div>
    </Suspense>
  );
}
