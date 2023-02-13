import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";

export function EventTable({ rows, cells, deleteFun, navigate }) {
  return (
    <TableContainer component={Paper}>
      <Table sx={{}} aria-label="simple table">
        <TableHead>
          <TableRow>
            {cells?.map((cell) => {
              return (
                <TableCell key={cell.id} align="left">
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
              onClick={() => {
                navigate(`detailEvent/${row.id}`);
              }}
            >
              <TableCell align="left">{row.id}</TableCell>
              <TableCell align="left">{row.title}</TableCell>
              <TableCell align="left">{row.startDate}</TableCell>
              <TableCell align="left">{row.endDate}</TableCell>
              <TableCell align="left">
                <button
                  onClick={() => {
                    deleteFun(row.id);
                  }}
                >
                  삭제
                </button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

export function MyUploadCoursesTable({ rows, cells, deleteFun, navigate }) {}

export function AdminUserTable({ rows, cells, deleteFun }) {
  return (
    <TableContainer component={Paper}>
      <Table sx={{}} aria-label="simple table">
        <TableHead>
          <TableRow>
            {cells?.map((cell) => {
              return (
                <TableCell key={cell.id} align="left">
                  {cell?.name}
                </TableCell>
              );
            })}
          </TableRow>
        </TableHead>
        <TableBody>
          {rows?.map((row, idx) => (
            <TableRow key={row.id}>
              <TableCell align="left">{row.id}</TableCell>
              <TableCell align="left">{row.account}</TableCell>
              <TableCell align="left">{row.nickname}</TableCell>
              <TableCell align="left">{row.mobile}</TableCell>
              <TableCell align="left">{row.loginType}</TableCell>
              <TableCell align="left">{row.creator}</TableCell>
              <TableCell align="left">{row.joinedDate.slice(0, 10)}</TableCell>
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
                <TableCell key={cell.id} align="left">
                  {cell?.name}
                </TableCell>
              );
            })}
          </TableRow>
        </TableHead>
        <TableBody>
          {rows?.map((row, idx) => (
            <TableRow key={row.id}>
              <TableCell align="left">
                {row.activated ? "승인" : "미승인"}
              </TableCell>
              <TableCell align="left">{row.name}</TableCell>
              <TableCell align="left">{row.email}</TableCell>
              <TableCell align="left">{row.mobile}</TableCell>
              <TableCell align="left">{row.subject}</TableCell>
              <TableCell align="left">{row.creator}</TableCell>
              <TableCell align="left">
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
                <TableCell key={cell.id} align="left">
                  {cell?.name}
                </TableCell>
              );
            })}
          </TableRow>
        </TableHead>
        <TableBody>
          {rows?.map((row, idx) => (
            <TableRow key={row.id}>
              <TableCell align="left">{row.creatorId}</TableCell>
              <TableCell align="left">{row.name}</TableCell>
              <TableCell align="left">{row.email}</TableCell>
              <TableCell align="left">{row.mobile}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
