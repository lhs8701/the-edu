import { FormControl, InputLabel, NativeSelect } from "@mui/material";
import { Box } from "@mui/system";
import { CREATOR_BAR_LIST } from "../../static";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";

export default function Revisecourses() {
  return (
    <>
      {/* <DashboardTitleTab title={CREATOR_BAR_LIST[2].list[3]} /> */}
      <Box sx={{ minWidth: 120 }}>
        <FormControl fullWidth>
          <InputLabel variant="standard" htmlFor="uncontrolled-native">
            Age
          </InputLabel>
          <NativeSelect
            defaultValue={30}
            inputProps={{
              name: "age",
              id: "uncontrolled-native",
            }}
          >
            <option value={10}>Ten</option>
            <option value={20}>Twenty</option>
            <option value={30}>Thirty</option>
          </NativeSelect>
        </FormControl>
      </Box>
    </>
  );
}
