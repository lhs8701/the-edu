import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import styled from "styled-components";

const DeleteBtn = styled.button`
  z-index: 10;
  border: none;
  padding: 5px;
`;

export function EventTable({ rows, cells, deleteFun, navigate }) {
  return (
    <TableContainer component={Paper}>
      <Table sx={{}} aria-label="simple table">
        <TableHead>
          <TableRow>
            {cells?.map((cell) => {
              return (
                <TableCell key={cell.id} align="center">
                  {cell?.name}
                </TableCell>
              );
            })}
          </TableRow>
        </TableHead>
        <TableBody>
          {rows?.map((row, idx) => (
            <TableRow
              sx={{
                cursor: "pointer",
              }}
              key={row.id}
            >
              <TableCell align="center">{row.id}</TableCell>
              <TableCell align="center">{row.title}</TableCell>
              <TableCell align="center">{row.startDate}</TableCell>
              <TableCell align="center">{row.endDate}</TableCell>
              <TableCell align="center">
                <DeleteBtn
                  onClick={() => {
                    deleteFun(row.id);
                  }}
                >
                  삭제
                </DeleteBtn>
              </TableCell>
              <TableCell align="center">
                <DeleteBtn
                  onClick={() => {
                    navigate(`detailEvent/${row.id}`);
                  }}
                >
                  보기
                </DeleteBtn>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

export function MyUploadCoursesTable({ rows, cells, deleteFun, navigate }) {
  return (
    <TableContainer component={Paper}>
      <Table sx={{}} aria-label="simple table">
        <TableHead>
          <TableRow>
            {cells?.map((cell) => {
              return (
                <TableCell key={cell.id} align="center">
                  {cell?.name}
                </TableCell>
              );
            })}
          </TableRow>
        </TableHead>
        <TableBody>
          {rows?.map((row, idx) => (
            <TableRow key={row.id}>
              <TableCell align="center">{row.courseId}</TableCell>
              <TableCell align="center">{row.title}</TableCell>
              <TableCell align="center">{row.category}</TableCell>
              <TableCell align="center">
                {row.creator ? "맞음" : "아님"}
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
export function CouponTable({ rows, cells, navigate }) {
  console.log(rows);
  return (
    <TableContainer component={Paper}>
      <Table sx={{}} aria-label="simple table">
        <TableHead>
          <TableRow>
            {cells?.map((cell) => {
              return (
                <TableCell key={cell.id} align="center">
                  {cell?.name}
                </TableCell>
              );
            })}
          </TableRow>
        </TableHead>
        <TableBody>
          {rows?.map((row, idx) => (
            <TableRow key={row.id}>
              <TableCell align="center">{row.id}</TableCell>
              <TableCell align="center">{row.name}</TableCell>
              <TableCell align="center">
                {row.discountPolicy === "Rate" ? "고정 할인" : "비율 할인"}
              </TableCell>
              <TableCell align="center">{row.discount}</TableCell>
              <TableCell align="center">{row.endDate}</TableCell>
              <TableCell align="center">
                {row.expired ? "만료" : "유효"}
              </TableCell>
              <TableCell align="center">
                <button
                  onClick={() => {
                    navigate(`${row.id}`, { state: row.code });
                  }}
                >
                  상세 보기
                </button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

export function AdminUserTable({ rows, cells, deleteFun }) {
  return (
    <TableContainer component={Paper}>
      <Table sx={{}} aria-label="simple table">
        <TableHead>
          <TableRow>
            {cells?.map((cell) => {
              return (
                <TableCell key={cell.id} align="center">
                  {cell?.name}
                </TableCell>
              );
            })}
          </TableRow>
        </TableHead>
        <TableBody>
          {rows?.map((row, idx) => (
            <TableRow key={row.id}>
              <TableCell align="center">{row.id}</TableCell>
              <TableCell align="center">{row.account}</TableCell>
              <TableCell align="center">{row.nickname}</TableCell>
              <TableCell align="center">{row.mobile}</TableCell>
              <TableCell align="center">{row.loginType}</TableCell>
              <TableCell align="center">
                {row.creator ? "맞음" : "아님"}
              </TableCell>
              <TableCell align="center">
                {row.joinedDate.slice(0, 10)}
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

export function AdminCreatorStanbyTable({ rows, cells, activeFun }) {
  return (
    <TableContainer component={Paper}>
      <Table sx={{}} aria-label="simple table">
        <TableHead>
          <TableRow>
            {cells?.map((cell) => {
              return (
                <TableCell key={cell.id} align="center">
                  {cell?.name}
                </TableCell>
              );
            })}
          </TableRow>
        </TableHead>
        <TableBody>
          {rows?.map((row, idx) => (
            <TableRow key={row.id}>
              <TableCell align="center">
                {row.activated ? "승인" : "미승인"}
              </TableCell>
              <TableCell align="center">{row.name}</TableCell>
              <TableCell align="center">{row.email}</TableCell>
              <TableCell align="center">{row.mobile}</TableCell>
              <TableCell align="center">{row.subject}</TableCell>
              <TableCell align="center">{row.creator}</TableCell>
              <TableCell align="center">
                <button
                  onClick={() => {
                    activeFun(row.creatorId);
                  }}
                >
                  승인
                </button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

export function AdminCreatorsTable({ rows, cells, activeFun }) {
  return (
    <TableContainer component={Paper}>
      <Table sx={{}} aria-label="simple table">
        <TableHead>
          <TableRow>
            {cells?.map((cell) => {
              return (
                <TableCell key={cell.id} align="center">
                  {cell?.name}
                </TableCell>
              );
            })}
          </TableRow>
        </TableHead>
        <TableBody>
          {rows?.map((row, idx) => (
            <TableRow key={row.id}>
              <TableCell align="center">{row.creatorId}</TableCell>
              <TableCell align="center">{row.name}</TableCell>
              <TableCell align="center">{row.email}</TableCell>
              <TableCell align="center">{row.mobile}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
