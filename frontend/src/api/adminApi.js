import axios from "axios";
import { BASE_URL } from "../static";

const ADMIN_URL = `${BASE_URL}/admin-api`;
const GET_USERS_URL = `${ADMIN_URL}/members`;
const GET_CREATOR_STANBY_URL = `${ADMIN_URL}/creators/standby`;
const CREATOR_URL = `${ADMIN_URL}/creators`;
const POST_GENERATE_COUPON_URL = `${ADMIN_URL}/coupons/generate`;
const GET_READYCOURSE_URL = `${ADMIN_URL}/courses/inactive`;
const POST_ACTIVECOURSE_URL = `${ADMIN_URL}/courses`;
const COUPON_LIST_URL = `${ADMIN_URL}/coupons`;
const GET_STOP_COURSES_URL = `${ADMIN_URL}/courses/locked`;
const GET_LOCKED_MEMBER_URL = `${GET_USERS_URL}/locked`;
const GET_UNIT_INFO_URL = `${ADMIN_URL}/courses/units`;
const GET_ALL_PROFIT_URL = `${ADMIN_URL}/status`;

export async function getAllProfitApi(accessToken) {
  return await axios.get(GET_ALL_PROFIT_URL, {
    headers: {
      "Content-Type": "application/json",
      ACCESS: accessToken,
    },
  });
}

export async function getAdminVideoInfoApi(accessToken, unitId) {
  return await axios.get(
    `${GET_UNIT_INFO_URL}/${unitId}`,

    {
      headers: {
        "Content-Type": "application/json",
        ACCESS: accessToken,
      },
    }
  );
}

export async function unLockedMembersApi(accessToken, memberId) {
  return await axios.post(
    `${GET_USERS_URL}/${memberId}/unlock`,
    {},
    {
      headers: {
        "Content-Type": "application/json",
        ACCESS: accessToken,
      },
    }
  );
}

export async function lockedMembersApi(accessToken, memberId) {
  return await axios.post(
    `${GET_USERS_URL}/${memberId}/lock`,
    {},
    {
      headers: {
        "Content-Type": "application/json",
        ACCESS: accessToken,
      },
    }
  );
}

export async function getLockedMembersApi(accessToken) {
  return await axios.get(GET_LOCKED_MEMBER_URL, {
    headers: {
      "Content-Type": "application/json",
      ACCESS: accessToken,
    },
  });
}

export async function getStopCoursesApi(accessToken) {
  return await axios.get(GET_STOP_COURSES_URL, {
    headers: {
      "Content-Type": "application/json",
      ACCESS: accessToken,
    },
  });
}
export async function lockCoursesApi(accessToken, courseId) {
  return await axios.post(
    `${POST_ACTIVECOURSE_URL}/${courseId}/lock`,
    {},
    {
      headers: {
        "Content-Type": "application/json",
        ACCESS: accessToken,
      },
    }
  );
}
export async function unlockCoursesApi(accessToken, courseId) {
  return await axios.post(
    `${POST_ACTIVECOURSE_URL}/${courseId}/unlock`,
    {},
    {
      headers: {
        "Content-Type": "application/json",
        ACCESS: accessToken,
      },
    }
  );
}

export async function importCouponApi(accessToken, counponId, memberId) {
  return await axios.post(
    `${COUPON_LIST_URL}/${counponId}/issue`,
    { memberId: memberId },
    {
      headers: {
        "Content-Type": "application/json",
        ACCESS: accessToken,
      },
    }
  );
}

export async function activeCourseApi(accessToken, courseId) {
  return await axios.post(
    `${POST_ACTIVECOURSE_URL}/${courseId}/activate`,
    {},
    {
      headers: {
        "Content-Type": "application/json",
        ACCESS: accessToken,
      },
    }
  );
}

export async function getReadyCourseListApi(accessToken) {
  return await axios.get(GET_READYCOURSE_URL, {
    headers: {
      "Content-Type": "application/json",
      ACCESS: accessToken,
    },
  });
}

export async function getAllCouponListApi(accessToken) {
  return await axios.get(COUPON_LIST_URL, {
    headers: {
      "Content-Type": "application/json",
      ACCESS: accessToken,
    },
  });
}

export async function generateCouponApi(accessToken, data) {
  return await axios.post(
    POST_GENERATE_COUPON_URL,
    {
      name: data.name,
      minimumAmount: data.amount,
      discountPolicy: data.policy,
      discount: data.discount,
      endDate: data.endDate,
    },
    {
      headers: {
        "Content-Type": "application/json",
        ACCESS: accessToken,
      },
    }
  );
}

export async function generateCouponCodeApi(accessToken, counponId, count) {
  return await axios.post(
    `${COUPON_LIST_URL}/${counponId}/code`,
    {},
    {
      headers: {
        "Content-Type": "application/json",
        ACCESS: accessToken,
      },
      params: {
        count: count,
      },
    }
  );
}

export async function getAllUsersApi(accessToken) {
  return await axios.get(GET_USERS_URL, {
    headers: {
      "Content-Type": "application/json",
      ACCESS: accessToken,
    },
  });
}

export async function getAllCreatorStanbyApi(accessToken) {
  return await axios.get(GET_CREATOR_STANBY_URL, {
    headers: {
      "Content-Type": "application/json",
      ACCESS: accessToken,
    },
  });
}

export async function getAllCreatorsApi(accessToken) {
  return await axios.get(CREATOR_URL, {
    headers: {
      "Content-Type": "application/json",
      ACCESS: accessToken,
    },
  });
}

export async function activateCreator(accessToken, creatorId) {
  return await axios.post(
    `${CREATOR_URL}/${creatorId}/active`,
    {},
    {
      headers: {
        "Content-Type": "application/json",
        ACCESS: accessToken,
      },
    }
  );
}
