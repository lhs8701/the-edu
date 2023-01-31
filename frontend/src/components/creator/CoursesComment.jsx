import { Typography } from "@mui/material";
import { useState } from "react";
import { BASE_URL } from "../../static";

export default function CoursesComment() {
  const [file, setFile] = useState("");
  const upload = (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append("multipartFile", file);
    fetch(`${BASE_URL}/test/image`, {
      method: "POST",
      body: formData,
      keepalive: true,
    })
      .then((res) => res)
      .then((data) => {
        alert(data);
      })
      .catch((err) => {
        alert(err);
      });
  };

  return (
    <div>
      <Typography variant="h4" mb={1}>
        강좌 댓글 관리
      </Typography>
      <input
        type="file"
        id="summary_image"
        accept=".jpg, .jpeg, .png"
        placeholder="Attach File"
        onChange={(e) => {
          setFile(e.target.files[0]);
        }}
      />
      <button onClick={upload}>업로드</button>
    </div>
  );
}
