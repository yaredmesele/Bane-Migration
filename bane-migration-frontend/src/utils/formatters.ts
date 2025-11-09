import { format } from 'date-fns'

const ISO_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSX"

export function formatDateTime(value: string | null, fallback = 'Not available'): string {
  if (!value) {
    return fallback
  }

  const parsedDate = new Date(value)

  if (Number.isNaN(parsedDate.getTime())) {
    return fallback
  }

  return format(parsedDate, 'PPpp')
}

export function formatPercentage(numerator: number, denominator: number): string {
  if (denominator === 0) {
    return '0%'
  }

  const ratio = (numerator / denominator) * 100
  return `${ratio.toFixed(1)}%`
}

export function formatIso(value: string | null): string {
  if (!value) {
    return ''
  }

  const parsedDate = new Date(value)

  if (Number.isNaN(parsedDate.getTime())) {
    return value
  }

  return format(parsedDate, ISO_DATE_PATTERN)
}

