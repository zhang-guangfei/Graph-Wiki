import Cookies from 'js-cookie'

const TokenKey = 'Admin-Frame-Token'
const TokenSso = 'Admin-Frame-Name'
const TokenRefreshKey = 'Admin-Frame-Refresh-Token'
const PositionKey = 'Admin-Frame-Position'
const DailyConditionKey = 'dailycondition'
const DailyPageKey = 'dailypage'
const MarketConditionKey = 'marketcondition'
const MarketPageKey = 'marketpage'
const AgentmarketConditionKey = 'agentmarketcondition'
const AgentmarketPageKey = 'agentmarketpage'
const SalesplanConditionKey = 'condition'
const SalesplanPageKey = 'page'

export function getToken() {
  return Cookies.get(TokenKey)
}
// C14717 bugid:14930  2024/8/14
export function getTokenSso() {
  return Cookies.get(TokenSso)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}
// C14717 bugid:14930  2024/8/14
export function setTokenSso(token) {
  return Cookies.set(TokenSso, token)
}

export function removeToken() {
  // C14717 bugid:14930  2024/8/14
  Cookies.remove(TokenSso)
  return Cookies.remove(TokenKey)
}

export function setRefreshToken(refreshToken) {
  return Cookies.set(TokenRefreshKey, refreshToken)
}

export function getRefreshToken() {
  return Cookies.get(TokenRefreshKey)
}

export function removeRefreshToken() {
  return Cookies.remove(TokenRefreshKey)
}

export function getPosition() {
  return Cookies.get(PositionKey)
}

export function setPosition(position) {
  return Cookies.set(PositionKey, position)
}

export function removePosition() {
  return Cookies.remove(PositionKey)
}

export function removeDailyConditionToken() {
  return Cookies.remove(DailyConditionKey)
}

export function removeDailyPageToken() {
  return Cookies.remove(DailyPageKey)
}

export function removeMarketConditionToken() {
  return Cookies.remove(MarketConditionKey)
}

export function removeMarketPageToken() {
  return Cookies.remove(MarketPageKey)
}

export function removeAgentMarketConditionToken() {
  return Cookies.remove(AgentmarketConditionKey)
}

export function removeAgentMarketPageToken() {
  return Cookies.remove(AgentmarketPageKey)
}

export function removeSalesplanConditionToken() {
  return Cookies.remove(SalesplanConditionKey)
}

export function removeSalesplanPageToken() {
  return Cookies.remove(SalesplanPageKey)
}

export function isShowGoogleChromeTip() {
  const USER_AGENT = navigator.userAgent.toLowerCase()
  const isChrome = /.*(chrome)\/([\w.]+).*/
  const isFirefox = /.*(firefox)\/([\w.]+).*/
  return !isChrome.test(USER_AGENT) && !isFirefox.test(USER_AGENT)
}

